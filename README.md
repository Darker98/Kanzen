# Kanzen - A Comprehensive Kanban Board

## Table of Contents
- [Introduction](#introduction)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Database Structure](#database-structure)
- [SignalR Integration](#signalr-integration)
- [Authentication](#authentication)
- [Learning Objectives Achieved](#learning-objectives-achieved)
- [License](#license)

## Introduction
Kanzen is a desktop Kanban board designed to streamline workflow processes and enhance productivity. It is built using JavaFX for the frontend and Java for the backend, with C# handling SignalR client code and authentication. Kanzen is hosted on Microsoft Azure and uses Azure Cosmos DB for database storage.

## Features
- **Task Management**: Visual representation of tasks using Kanban methodology.
- **Real-Time Synchronization**: Instant updates across clients using SignalR.
- **User Authentication**: Secure login and access control via Microsoft Entra ID.
- **Scalability and Cloud Hosting**: Hosted on Microsoft Azure with a NoSQL database for efficient data management.

## Technologies Used
- **Frontend**: JavaFX with GemsFX components for an intuitive UI, assisted by SceneBuilder.
- **Backend**: Java for core functionalities.
- **Real-Time Communication**: SignalR (C#) for live updates.
- **Database**: Azure Cosmos DB (NoSQL storage solution).
- **Authentication**: Microsoft Entra ID.
- **Hosting**: Microsoft Azure for deployment and scalability.

## Database Structure
Kanzen uses Azure Cosmos DB with two main collections:
1. **Users Collection**:
   - `id`: Unique user identifier.
   - `name`: User's full name.
   - `email`: User's email address.
   - `status`: Role in the system (employee or manager).
   - `boardId`: Associated board ID.
2. **Boards Collection**:
   - `boardId`: Unique identifier for the board.
   - `boardName`: Name of the board.
   - `userId`: Identifier of the board owner.

## SignalR Integration
SignalR enables real-time collaboration by connecting all clients and ensuring synchronization. It notifies users when a card is added, moved, or modified, keeping all board members updated instantly.

## Authentication
Kanzen leverages Microsoft Entra ID for user authentication. This ensures secure access control and seamless integration with Microsoft services.

## Learning Objectives Achieved
- Implementation of a desktop application using JavaFX and SceneBuilder.
- Integration of real-time communication using SignalR.
- Secure authentication using Microsoft Entra ID.
- Cloud database management with Azure Cosmos DB.
- Deployment and scalability on Microsoft Azure.

## License
This project is licensed under the MIT License.
