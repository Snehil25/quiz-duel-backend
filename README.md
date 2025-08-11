# Quiz Duel - Real-Time 2-Player Quiz Game

A full-stack, real-time quiz game application built with a Spring Boot backend and a vanilla JavaScript frontend. Players can register, create or join games, and compete in a 10-question duel with live updates.

---

## Features ✨

* **User Authentication:** Secure user registration and login using JWT (JSON Web Tokens).
* **User Profiles:** Endpoints to view and update user profiles, including preferred quiz categories.
* **Game Lobby:** Create a new game to get an invite code or join an existing game.
* **Real-Time Gameplay:** Uses WebSockets (with STOMP) for live updates when players join and answer questions.
* **Dynamic Quizzes:** Questions are randomly selected from a pool, with the option to use a player's preferred categories.
* **Scoring & Summary:** Correct answers award points, and a final summary screen declares the winner.
* **Game History:** Players can view a history of their past finished matches, including the opponent and final scores.
* **Robust Backend:** Includes global exception handling and secure configuration.

---

## Tech Stack 🛠️

* **Backend:** Java 11+, Spring Boot, Spring Security, Spring Data JPA, JWT
* **Database:** H2 In-Memory Database
* **Real-Time:** Spring WebSockets, STOMP
* **Frontend:** HTML5, Tailwind CSS, Vanilla JavaScript (with SockJS & StompJS clients)
* **Build Tool:** Apache Maven

---

## How to Run Locally 🚀

1.  **Prerequisites:**
    * Java JDK 11 or newer
    * Apache Maven

2.  **Clone the repository:**
    ```bash
    git clone https://github.com/Snehil25/quiz-duel-backend
    cd quiz-duel-backend
    ```

3.  **Run the backend:**
    * You can run the `QuizDuelApplication.java` file from your IDE (like IntelliJ or STS).
    * Or, you can run it from the command line using Maven:
        ```bash
        mvn spring-boot:run
        ```
    * The backend server will start on `http://localhost:8080`.

4.  **Play the game:**
    * Open a web browser and navigate to **`http://localhost:8080`**.
    * The `index.html` file will be served automatically.
    * Open two browser windows to simulate two different players!

---

## Seed Data

The application is seeded with **80 questions** across four categories (Science, History, Technology, Geography) from the `src/main/resources/data.sql` file.
