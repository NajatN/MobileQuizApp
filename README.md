# 📱 Android Quiz App

An interactive Android quiz application built using **Java**, **MVVM architecture**, and **Firebase Firestore**.  
Users can choose their quiz level, answer randomized questions, receive instant feedback, and view their quiz history stored in the cloud.

---

## 📌 Features

### 🎯 Dynamic Quiz Flow
- Choose quiz difficulty level  
- Questions pulled in real-time from Firebase  
- Separate handling of single-choice and double-choice questions  
- Answer validation with visual feedback  
- Automatic question progression  

### 📊 Result Tracking
- Score calculation at the end of each quiz  
- Feedback summary (good / average / needs improvement)  
- Results saved to Firestore  
- Local & remote history support via RecyclerView  

### 🔥 Firebase Integration
- Firestore for:
  - Quiz questions organized by level
  - User quiz history
- Smooth async loading with `LoadingFragment`  

### 🧠 MVVM Architecture
- `QuizViewModel` for managing quiz state  
- Repository pattern for Firestore operations  
- Fragments communicate via shared ViewModel  

### 🖼️ Modern UI
- Clean fragment-based navigation  
- Progress indicators  
- Icons for answer correctness  
- Smooth transitions between quiz screens  

---

## 🔧 Tech Stack

### Languages & Tools
- Java  
- Android Studio  
- XML Layouts  

### Architecture
- MVVM  
- LiveData  
- Fragment navigation  

### Firebase
- Firestore Database  
- Google Services plugin  

### UI Components
- RecyclerView  
- ProgressBar  
- ImageView  
- Custom animations  

---

## 🚀 How It Works

### 1. Start Screen
User taps **Start** and begins the quiz flow.

### 2. Choose Difficulty
Users select a quiz level. Firestore determines which question set loads.

### 3. Question Flow
- Questions loaded from Firestore  
- ViewModel tracks selection, correctness, index, and score  
- Both single-answer and double-answer question types supported  

### 4. Loading Screen
`LoadingFragment` provides a smooth transition while data loads.

### 5. Results
- Final score shown  
- Feedback message displayed  
- Result saved to Firestore  

### 6. Quiz History
A RecyclerView lists:
- Score  
- Total questions  
- Date  
- Level  

---

## 🗂️ Firebase Structure (example)

**Questions Collection**  
`questions/{level}/[question documents]`  
- `questionText`: string  
- `options`: list<string>  
- `correctAnswers`: list<int>  
- `type`: "single" | "double"  

**History Collection**  
`history/{userId}/[results]`  
- `score`: int  
- `totalQuestions`: int  
- `date`: timestamp  
- `level`: string

---

## 🛠️ Installation & Setup

### 1. Clone the repository
git clone https://github.com/yourusername/your-quiz-app.git

### 2. Open in Android Studio
Open the project folder in Android Studio.

### 3. Add Firebase configuration
Place the google-services.json file in the /app directory.

### 4. Sync Gradle
Use Sync Project with Gradle Files in Android Studio.

### 5. Run the app
Launch the app on an emulator or a connected device.

---

## 🤝 Contributing
Pull requests and suggestions are welcome.
