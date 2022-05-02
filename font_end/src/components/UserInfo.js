import React from "react";
import { useState, useEffect } from "react";
import { Navigate } from "react-router-dom";
import UserService from "../services/user/UserService";
import { useSelector } from "react-redux";
import { loginSelector } from "../store/reducers/loginSlice";
// import { useDispatch } from "react-redux";

const UserInfo = () => {
  const [userInformation, setUserInformation] = useState("");

  //   const dispatch = useDispatch();
  const loginInfo = useSelector(loginSelector);

  useEffect(() => {
    if (loginInfo.isLogged) {
      UserService.getUserInfo(loginInfo.token).then(async (res) => {
        console.log(res.data);
        await setUserInformation(res.data);
      });
    }
  }, [loginInfo.isLogged]);

  return (
    <div>
      <table className="User-information">
        <thead>
          <tr>
            <th> firstName </th>
            <th> lastName </th>
            <th> username </th>
            <th> phoneNumber </th>
            <th> address </th>
            <th> roles </th>
          </tr>
        </thead>
        <tbody>
          <tr key={userInformation.id}>
            <td> {userInformation.firstName}</td>
            <td> {userInformation.lastName}</td>
            <td> {userInformation.username}</td>
            <td> {userInformation.phoneNumber}</td>
            <td> {userInformation.address}</td>
            {/* <td> {userInformation.roles}</td> */}
          </tr>
        </tbody>
      </table>
      <div>{JSON.stringify(userInformation.roles)}</div>
    </div>
  );
};

export default UserInfo;
