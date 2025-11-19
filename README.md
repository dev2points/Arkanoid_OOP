# Arkanoid Game – Object-Oriented Programming Project

## Author
Group — Class OOP (Java)

1. Đặng Anh Phương — 24020277  
2. Nguyễn Minh Nhật — 24020259  
3. Ngô Thị Thảo Vân — 24020358  
3. Đinh Đức Thịnh — 24020313  

**Instructor:** Kiều Văn Tuyên  
**Semester:** HKI — Năm học 2025–2026  

---

## Description
This is a classic Arkanoid game developed in Java as a final project for the Object-Oriented Programming course.  
The project demonstrates the implementation of OOP principles and several well-known design patterns.

### Key features:
1. Developed using **Java 17+** with **JavaFX** for GUI.  
2. Implements core OOP principles: **Encapsulation**, **Inheritance**, **Polymorphism**, **Abstraction**.  
3. Applies multiple design patterns:  **Strategy**  
4. Includes **sound effects**, **animations**, and a **power-up system**.  
5. Supports **save/load game** and **leaderboard** functionality.

### Game mechanics:
- Control a paddle to bounce the ball and destroy bricks.  
- Collect power-ups for special abilities.  
- Progress through multiple levels with increasing difficulty.
- Defeat the final boss and win
- Score points and compete on the leaderboard.  

---

## UML Diagram

### Class Diagram
(Complete UML diagrams are available in the `docs/uml/` folder.)

---

## Design Patterns Implementation
-Use **Strategy** to defines common behavior for abstract class powerups

---

## Multithreading Implementation
No use multithreads

---

## Installation
1. Clone the project from this repository.  
2. Open the project in your preferred IDE (IntelliJ IDEA recommended).  
3. Build the project using **Maven**.  
4. Run the application.

---

## Usage

### Controls
For the seconds player in double player mode or single player mode
| Key | Action |
|-----|---------|
| ← | Move paddle left |
| → | Move paddle right |
| ↑ | Launch ball |
|  ESC | Pause game |

For the first player in double player mode 
| Key | Action |
|-----|---------|
| A | Move paddle left |
| D | Move paddle right |
| W | Launch ball |
|  ESC | Pause game |


### How to Play
1. Start the game from the main menu.  
2. Move the paddle  
3. Press ↑ / W to launch the ball.  
4. Bounce the ball to destroy bricks.  
5. Collect power-ups to gain special abilities.  
6. Avoid letting the ball fall.  
7. Clear all bricks to complete the level.
8. Fight against final strong boss to win game.

---

## Power-ups
| Icon | Name | Effect |
|------|-------|---------|
| 🟦 | Extend Paddle | Increase paddle width |
| 🟥 | Shrink Paddle | Decrease paddle width |
| 🔮 | Multi Ball | Splits ball into several balls |
| 🔫 | Laser Gun | Paddle can shoot lasers |
| 🛡 | ExtraHP | Increase life |
| 🔥 | Fire Ball | Ball can pass through bricks |

---

## Scoring System
- 1 hit of ball and brick or boss gets 1 point

---



## Technologies Used
| Technology | Version | Purpose |
|------------|----------|-----------|
| Java | 17+ | Main programming language |
| JavaFX | 19.0.2 | GUI framework |
| Maven | 3.9+ | Build tool |
| CSS | 2.15.0 | Effect button |

---

## License
This project is developed for educational purposes only.  
Please follow your institution’s academic integrity policies.

---

## Notes
- This project is part of the **Object-Oriented Programming** course.  
- All code is implemented by group members under instructor guidance.  
- Some media assets are used under fair use for educational purposes.

---

## About
No description, website, or topics provided.
