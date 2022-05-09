import { createSlice } from "@reduxjs/toolkit";

const loginSlice = createSlice({
  name: "login",
  initialState: {
    loginInfo: {
      token: "",
      refreshToken: "",
      username: "",
      firstName: "",
      lastName: "",
      phoneNumber: "",
      address: "",
      userRoles: "",
      isLogged: false,
      isAdmin: false,
      isLoading: true,
    },
  },

  reducers: {
    loginAction: (state, action) => {
      state.loginInfo.token = action.payload.token;
      state.loginInfo.username = action.payload.username;
      state.loginInfo.firstName = action.payload.firstName;
      state.loginInfo.lastName = action.payload.lastName;
      state.loginInfo.phoneNumber = action.payload.phoneNumber;
      state.loginInfo.address = action.payload.address;
      state.loginInfo.refreshToken = action.payload.refreshToken;
      state.loginInfo.userRoles = action.payload.userRoles;
      state.loginInfo.isAdmin = action.payload.userRoles.includes("ADMIN");
      state.loginInfo.isLogged = true;
    },
    startLoadingAction: (state) => {
      state.loginInfo.isLoading = true;
    },
    endLoadingAction: (state) => {
      state.loginInfo.isLoading = false;
    },

    logoutAction: (state) => {
      state.loginInfo.token = "";
      state.loginInfo.username = "";
      state.loginInfo.firstName = "";
      state.loginInfo.lastName = "";
      state.loginInfo.phoneNumber = "";
      state.loginInfo.address = "";
      state.loginInfo.refreshToken = "";
      state.loginInfo.userRoles = "";
      state.loginInfo.isLogged = false;
      state.loginInfo.isAdmin = false;
      localStorage.removeItem("userLoginInfo");
    },
  },
});

// Reducer
const loginReducer = loginSlice.reducer;

// Selector
export const loginSelector = (state) => state.loginReducer.loginInfo;

// Action Export
export const {
  loginAction,
  logoutAction,
  startLoadingAction,
  endLoadingAction,
} = loginSlice.actions;

// Export reducer
export default loginReducer;
