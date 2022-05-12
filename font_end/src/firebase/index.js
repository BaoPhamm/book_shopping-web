import firebase from "firebase/app";
import "firebase/storage";

// Your web app's Firebase configuration
const firebaseConfig = {
  apiKey: "AIzaSyB3jb_CbH0tglIYXHWcTxXSQOCoDuK4oRk",
  authDomain: "shopping-web-a0eb0.firebaseapp.com",
  projectId: "shopping-web-a0eb0",
  storageBucket: "shopping-web-a0eb0.appspot.com",
  messagingSenderId: "750344288758",
  appId: "1:750344288758:web:94edb93421678281fbefd7",
};

firebase.initializeApp(firebaseConfig);
const storage = firebase.storage();

export default storage;
