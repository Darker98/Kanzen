using Microsoft.AspNetCore.SignalR;
using System.Threading.Tasks;

public class BroadcastHub : Hub
{
    public async Task SendMessage(string boardId, int[] numbers)
    {
        // Broadcast message to all clients connected to this boardId
        await Clients.Group(boardId).SendAsync("ReceiveMessage", numbers);
    }

    public async Task JoinGroup(string boardId)
    {
        // Add client to a group based on boardId
        await Groups.AddToGroupAsync(Context.ConnectionId, boardId);
    }

    public async Task LeaveGroup(string boardId)
    {
        // Remove client from the group based on boardId
        await Groups.RemoveFromGroupAsync(Context.ConnectionId, boardId);
    }
}