import React from "react";
import UserInfo from "../components/UserInfo";
import { Navigate } from "react-router-dom";
import { useSelector } from "react-redux";
import { loginSelector } from "../store/reducers/loginSlice";

const UserInformation = () => {
  const loginInfo = useSelector(loginSelector);
  if (!loginInfo.isLogged) {
    return <Navigate to="/login" />;
  }
  return (
    <div>
      <UserInfo />
    </div>
  );
};

export default UserInformation;
