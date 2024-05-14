using Microsoft.Identity.Client;
using Microsoft.Identity.Client.Broker;
using Microsoft.Identity.Client.Extensions.Msal;
using System;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Reflection.Metadata;
using System.Security.Principal;
using System.Text.Json;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Interop;

namespace Auth
{
    public partial class MainWindow : Window 
    {
        private static string ClientId = "29a2a3ed-3c5f-4c41-94f8-844ec7681268";

        // Note: Tenant is important for the quickstart.
        private static string Tenant = "organizations";
        private static string Instance = "https://login.microsoftonline.com/";

        // Set the API endpoint
        private static string graphAPIEndpoint = "https://graph.microsoft.com/v1.0/me";

        //Set the scope for API call to user.read
        private static string[] scopes = new string[] { "user.read" };

        private static IPublicClientApplication _clientApp;

        public MainWindow() 
        {
            // Create the public client application
            CreateApplication();

            string[] args = Environment.GetCommandLineArgs();

            performMain(args);
        }

        private async Task performMain(String[] args)
        {
            // No command-line arguments provided
            if (args.Length == 0)
            {
                Environment.Exit(1);
            }

            string command = args[1].ToLower();

            if (command == "login")
            {
                // Perform authentication
                await PerformAuthenticationAsync(_clientApp);
            }
            else if (command == "logout")
            {
                // Sign out the user
                await SignOutAsync(_clientApp);
            }
            // Invalid command-line argument
            else
            {
                Environment.Exit(1);
            }

            Environment.Exit(0); // successful exit
        }

        public static void CreateApplication()
        {
            // Configure broker options for Windows
            BrokerOptions brokerOptions = new BrokerOptions(BrokerOptions.OperatingSystems.Windows);

            // Create the public client application
            _clientApp = PublicClientApplicationBuilder.Create(ClientId)
                .WithAuthority($"{Instance}{Tenant}")
                .WithDefaultRedirectUri()
                .WithBroker(brokerOptions)
                .Build();

            // Configure cache helper
            MsalCacheHelper cacheHelper = CreateCacheHelperAsync().GetAwaiter().GetResult();

            // Let the cache helper handle MSAL's cache, otherwise the user will be prompted to sign-in every time.
            cacheHelper.RegisterCache(_clientApp.UserTokenCache);
        }

        private static async Task<MsalCacheHelper> CreateCacheHelperAsync()
        {
            // Configure storage properties
            // Since this is a WPF application, only Windows storage is configured
            var storageProperties = new StorageCreationPropertiesBuilder(
                $"{System.Reflection.Assembly.GetExecutingAssembly().GetName().Name}.msalcache.bin",
                MsalCacheHelper.UserRootDirectory).Build();

            // Create cache helper
            MsalCacheHelper cacheHelper = await MsalCacheHelper.CreateAsync(
                storageProperties,
                new TraceSource("MSAL.CacheTrace")).ConfigureAwait(false);

            return cacheHelper;
        }

        /// <summary>
        /// Call AcquireToken - to acquire a token requiring user to sign-in
        /// </summary>
        private async Task PerformAuthenticationAsync(IPublicClientApplication app)
        {
            AuthenticationResult authResult = null;

            // If the user signed in before, remember the account info from the cache
            IAccount firstAccount = (await app.GetAccountsAsync()).FirstOrDefault();

            // Otherwise, try with the Windows account
            if (firstAccount == null)
            {
                firstAccount = PublicClientApplication.OperatingSystemAccount;
            }

            try
            {
                authResult = await app.AcquireTokenSilent(scopes, firstAccount)
                                     .ExecuteAsync();
            }
            catch (MsalUiRequiredException ex)
            {
                // A MsalUiRequiredException happened on AcquireTokenSilent. 
                // This indicates you need to call AcquireTokenInteractive to acquire a token

                try
                {
                    authResult = await app.AcquireTokenInteractive(scopes)
                                         .WithParentActivityOrWindow(new WindowInteropHelper(this).Handle)
                                         .WithAccount(firstAccount)
                                         .ExecuteAsync();
                }
                // Error acquiring token
                catch (MsalException msalex)
                {
                    Environment.Exit(1);
                }
            }
            // Error acquiring token silently
            catch (Exception ex)
            {
                Environment.Exit(1);
            }

            if (authResult != null)
            {
                Console.WriteLine(await GetHttpContentWithToken(graphAPIEndpoint, authResult.AccessToken));
                DisplayBasicTokenInfo(authResult);
            }
        }

        /// <summary>
        /// Perform an HTTP GET request to a URL using an HTTP Authorization header
        /// </summary>
        /// <param name="url">The URL</param>
        /// <param name="token">The token</param>
        /// <returns>String containing the results of the GET operation</returns>
        static async Task<string> GetHttpContentWithToken(string url, string token)
        {
            var httpClient = new System.Net.Http.HttpClient();
            System.Net.Http.HttpResponseMessage response;
            try
            {
                var request = new System.Net.Http.HttpRequestMessage(System.Net.Http.HttpMethod.Get, url);
                // Add the token in Authorization header
                request.Headers.Authorization = new System.Net.Http.Headers.AuthenticationHeaderValue("Bearer", token);
                response = await httpClient.SendAsync(request);
                var content = await response.Content.ReadAsStringAsync();
                return content;
            }
            catch (Exception ex)
            {
                return ex.ToString();
            }
        }

        /// <summary>
        /// Display basic information contained in the token
        /// </summary>
        static void DisplayBasicTokenInfo(AuthenticationResult authResult)
        {
            if (authResult != null)
            {
                Console.WriteLine("Success");
                Console.WriteLine($"{authResult.UniqueId}");
                Console.WriteLine($"{authResult.Account.Username}");
                Console.WriteLine($"Token Expires: {authResult.ExpiresOn.ToLocalTime()}");
            }
        }

        /// <summary>
        /// Sign out the current user
        /// </summary>
        static async Task SignOutAsync(IPublicClientApplication app)
        {
            var accounts = await app.GetAccountsAsync();
            if (accounts.Any())
            {
                try
                {
                    await app.RemoveAsync(accounts.FirstOrDefault());
                    Console.WriteLine("Success");
                }
                catch (MsalException ex)
                {
                    Console.WriteLine("Error");
                    Environment.Exit(1);
                }
            }
        }
    }
}