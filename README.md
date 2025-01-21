### README.txt

---

#### **App Description**
The application is a Bitcoin price watcher with the ability to monitor other cryptocurrencies. Users can search for cryptocurrencies, save them to their device, and track price changes. Additionally, the app provides a feature to browse all cryptocurrencies based on their rank.

---

#### **Third-Party Libraries**
Below is a list of third-party libraries used in the application, excluding those provided by Google or AndroidX:

1. **Retrofit (2.11.0)**  
   - Description: A type-safe HTTP client for Android and Java. Used for network API calls.
   - Repository: [Retrofit GitHub](https://github.com/square/retrofit)

2. **Gson (2.11.0)**  
   - Description: A library for converting Java objects into JSON and vice versa. Used as a JSON converter for Retrofit.
   - Repository: [Gson GitHub](https://github.com/google/gson)

3. **Moshi (1.15.1, 1.14.0)**  
   - Description: A modern JSON library for Android and Java. Used as another JSON converter for Retrofit and for object serialization.
   - Repository: [Moshi GitHub](https://github.com/square/moshi)

4. **Dagger Hilt (2.55)**  
   - Description: A dependency injection library to reduce boilerplate code. Used for injecting ViewModels and repositories.
   - Repository: [Hilt GitHub](https://dagger.dev/hilt)

5. **Timber (5.0.1)**  
   - Description: A logger library that simplifies logging in Android.
   - Repository: [Timber GitHub](https://github.com/JakeWharton/timber)

6. **Vico (2.0.0-beta.7)**  
   - Description: A lightweight and flexible graphing library. Used for displaying price trends in chart format.
   - Repository: [Vico GitHub](https://github.com/patrykandpatrick/vico)

7. **Security Crypto (1.0.0)**  
   - Description: Provides an API for encryption and secure shared preferences.
   - Repository: [Android Security Crypto Documentation](https://developer.android.com/reference/androidx/security/crypto)

---

#### **Known Bugs**
1. **Error Handling for Paid Memberships (402 Errors):**  
   - If the API throws a 402 error (payment required), it is not handled gracefully across all screens.
   - On the `Coin Overview` screen, multiple dialog boxes may appear due to recurring 402 errors triggered by periodic refreshes.

2. **Multiple Dialog Issue:**  
   - When errors occur, dialog boxes are not consolidated, leading to stacking dialogs that can overwhelm the user interface.

3. **Insufficient Feedback on Search Screen:**  
   - The search screen lacks adequate user feedback when a 402 error occurs.

---

#### **App Design and Architecture**
The application is designed using the following principles:

1. **Architecture: MVVM (Model-View-ViewModel)**  
   - **Model:** Handles data operations such as fetching data from APIs or local storage (encrypted shared preferences). Retrofit and Flowables are used to manage network operations and data streaming.  
   - **ViewModel:** Acts as a bridge between the Model and View. It holds LiveData to reactively update the UI and implements adapter patterns to easily switch between network solutions like GraphQL or gRPC.  
   - **View:** The XML layouts use DataBinding for a seamless connection to the ViewModel, allowing updates directly from LiveData.

2. **Single Activity with Multiple Fragments**  
   - The app uses a single `MainActivity` with multiple fragments managed via the Navigation Component. This improves user experience by reducing resource usage and providing a smoother navigation flow.

3. **Recycler Views and Navigation Graph**  
   - The app uses RecyclerViews for displaying lists of cryptocurrencies efficiently and Navigation Graph for seamless in-app navigation.

4. **Encrypted Shared Preferences**  
   - Data like favorite cryptocurrencies are stored securely using Android’s Security Crypto API.

5. **Charts for Price Visualization**  
   - The app leverages the Vico library for displaying dynamic cryptocurrency price trends in chart format.

6. **LiveData and Flowables**  
   - **LiveData:** Used to observe changes in the app state and reflect them in the UI.  
   - **Flowables:** Utilized for continuous data streaming from the network layer to the UI layer.

7. **Room Database Omission**  
   - Room was intentionally omitted to simplify development and reduce the overhead of managing tables and queries.
