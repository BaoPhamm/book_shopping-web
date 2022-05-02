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
    },
  },

  reducers: {
    loginAction: (state, action) => {
      state.loginInfo.token = action.payload.token;
      state.loginInfo.username = action.payload.username;
      state.loginInfo.refreshToken = action.payload.refreshToken;
      state.loginInfo.userRoles = action.payload.userRoles;
      state.loginInfo.isLogged = true;
    },

    logoutAction: (state, action) => {
      state.loginInfo.token = "";
      state.loginInfo.username = "";
      state.loginInfo.refreshToken = "";
      state.loginInfo.userRoles = "";
      state.loginInfo.isLogged = false;
    },

    // reducer(state, action) {
    //   state.loginInfo.token = action.payload;
    // },
    // prepare(token) {
    //   return {
    //     payload: {
    //       token,
    //       isLogged: false,
    //     },
    //   };
    // },
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
