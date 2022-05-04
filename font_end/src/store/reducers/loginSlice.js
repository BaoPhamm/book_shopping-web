import { createSlice } from "@reduxjs/toolkit";

const loginSlice = createSlice({
  name: "login",
  initialState: {
    loginInfo: {
      token: "",
      refreshToken: "",
      username: "",
      userRoles: "",
      isLogged: false,
      isLoading: true,
    },
  },

  reducers: {
    loginAction: (state, action) => {
      state.loginInfo.isLoading = true;
      state.loginInfo.token = action.payload.token;
      state.loginInfo.username = action.payload.username;
      state.loginInfo.refreshToken = action.payload.refreshToken;
      state.loginInfo.userRoles = action.payload.userRoles;
      state.loginInfo.isLogged = true;
      state.loginInfo.isLoading = false;
    },

    logoutAction: (state, action) => {
      state.loginInfo.isLoading = true;
      state.loginInfo.token = "";
      state.loginInfo.username = "";
      state.loginInfo.refreshToken = "";
      state.loginInfo.userRoles = "";
      state.loginInfo.isLogged = false;
      state.loginInfo.isLoading = false;
      localStorage.removeItem("userLoginInfo");
    },
  },
});

// Reducer
const loginReducer = loginSlice.reducer;

// Selector
export const loginSelector = (state) => state.loginReducer.loginInfo;

// Action Export
export const { loginAction, logoutAction } = loginSlice.actions;

// Export reducer
export default loginReducer;
