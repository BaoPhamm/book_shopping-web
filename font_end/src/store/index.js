import { configureStore } from "@reduxjs/toolkit";
import loginReducer from "./reducers/loginSlice";

// Store
const store = configureStore({
  reducer: { loginReducer },
});

export default store;
