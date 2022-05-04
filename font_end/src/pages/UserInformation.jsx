import React from "react";
import UserInfo from "../components/UserInfo";
import { Navigate } from "react-router-dom";
import { useSelector } from "react-redux";
import { loginSelector } from "../store/reducers/loginSlice";
import { PopupContainer } from "../components/PopupForm/Container/index";

const UserInformation = () => {
  const loginInfo = useSelector(loginSelector);

  const triggerText = "Add user";
  const onSubmit = (event) => {
    event.preventDefault(event);
    console.log(event.target.name.value);
    console.log(event.target.email.value);
  };
  return !loginInfo.isLoading ? (
    loginInfo.isLogged ? (
      <div>
        <UserInfo />
        <PopupContainer triggerText={triggerText} onSubmit={onSubmit} />
      </div>
    ) : (
      <Navigate to="/login" />
    )
  ) : (
    ""
  );
};

export default UserInformation;
