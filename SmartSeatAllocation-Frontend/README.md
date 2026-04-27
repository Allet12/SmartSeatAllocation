# Smart Seat Allocation Frontend

React + Vite frontend for the Smart Seat Allocation backend.

## API setup

Create a local `.env` file in this folder with:

```env
VITE_API_BASE_URL=http://localhost:9091/smartseat
```

## Run

```powershell
npm install
npm run dev
```

The frontend expects the Spring Boot backend to be running on port `9091` with the context path `/smartseat`.
