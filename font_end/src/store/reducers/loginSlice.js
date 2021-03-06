import { createSlice } from "@reduxjs/toolkit";

const loginSlice = createSlice({
  name: "login",
  initialState: {
    loginInfo: {
      token: "",
      refreshToken: "",
      id: 0,
      username: "",
      firstName: "",
      lastName: "",
      phoneNumber: "",
      address: "",
      userRoles: "",
      isBlocked: true,
      isLogged: false,
      isAdmin: false,
      isAdminManager: false,
      isLoading: true,
    },
  },

  reducers: {
    loginAction: (state, action) => {
      state.loginInfo.token = action.payload.token;
      state.loginInfo.id = action.payload.id;
      state.loginInfo.username = action.payload.username;
      state.loginInfo.firstName = action.payload.firstName;
      state.loginInfo.lastName = action.payload.lastName;
      state.loginInfo.phoneNumber = action.payload.phoneNumber;
      state.loginInfo.address = action.payload.address;
      state.loginInfo.refreshToken = action.payload.refreshToken;
      state.loginInfo.userRoles = action.payload.userRoles;
      state.loginInfo.isAdmin = action.payload.userRoles.includes("ADMIN");
      state.loginInfo.isAdminManager =
        action.payload.userRoles.includes("ADMANAGER");
      state.loginInfo.isBlocked = action.payload.isBlocked;
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
      state.loginInfo.id = 0;
      state.loginInfo.username = "";
      state.loginInfo.firstName = "";
      state.loginInfo.lastName = "";
      state.loginInfo.phoneNumber = "";
      state.loginInfo.address = "";
      state.loginInfo.refreshToken = "";
      state.loginInfo.userRoles = "";
      state.loginInfo.isLogged = false;
      state.loginInfo.isAdmin = false;
      state.loginInfo.isAdminManager = false;
      state.loginInfo.isBlocked = true;
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
