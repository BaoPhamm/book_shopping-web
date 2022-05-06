import React, { useState, useEffect } from "react";
import styled from "styled-components";
import { mobile } from "../responsive";
import { Navigate } from "react-router-dom";
import RegistrationService from "../services/user/RegistrationService";
import { useSelector } from "react-redux";
import { loginAction } from "../store/reducers/loginSlice";
import { loginSelector } from "../store/reducers/loginSlice";
import { useDispatch } from "react-redux";
import AuthService from "../services/user/AuthService";
import UserService from "../services/user/UserService";

const Container = styled.div`
  width: 98vw;
  height: 98vh;
  // background: linear-gradient(
  //     rgba(255, 255, 255, 0.5),
  //     rgba(255, 255, 255, 0.5)
  //   ),
  //   url("https://images.pexels.com/photos/6984661/pexels-photo-6984661.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940")
  //     center;
  background-color: #ffe1f2;
  background-size: cover;
  display: flex;
  align-items: center;
  margin-top: 2rem;
  justify-content: center;
`;

const Wrapper = styled.div`
  width: 40%;
  padding: 20px;
  background-color: white;
  text-align: center;
  ${mobile({ width: "75%" })}
`;

const Title = styled.h1`
  font-size: 24px;
  font-weight: 300;
`;

const Form = styled.form`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const Input = styled.input`
  flex: 1;
  min-width: 90%;
  margin: 20px 10px 0px 0px;
  padding: 10px;
`;

const Agreement = styled.span`
  font-size: 12px;
  margin: 20px 0px;
`;

const Button = styled.button`
  width: 50%;
  border: none;
  left: 50%;
  padding: 15px 20px;
  background-color: teal;
  color: white;
  cursor: pointer;
`;

const UpdateProfile = () => {
  const [formUsername, setUsername] = useState("");
  const [formFirstName, setFirstName] = useState("");
  const [formLastName, setLastName] = useState("");
  const [formAddress, setAddress] = useState("");
  const [formPhoneNumber, setPhoneNumber] = useState("");
  const [formPassword, setPassword] = useState("");
  const [formPasswordRepeat, setPasswordRepeat] = useState("");
  const [userInformation, setUserInformation] = useState("");
  const [isLoading, setIsLoading] = useState(true);

  const loginInfo = useSelector(loginSelector);
  const dispatch = useDispatch();

  useEffect(() => {
    fetchData();
  }, [loginInfo.isLogged]);
  // <tr key={userInformation.id}>
  //   <td> {userInformation.firstName}</td>
  //   <td> {userInformation.lastName}</td>
  //   <td> {userInformation.username}</td>
  //   <td> {userInformation.phoneNumber}</td>
  //   <td> {userInformation.address}</td>
  const fetchData = async () => {
    await setIsLoading(true);
    if (loginInfo.isLogged) {
      UserService.getUserInfo(loginInfo.token).then(async (res) => {
        await setUserInformation(res.data);
      });
      setUsername();
    }
    await setIsLoading(false);
  };

  const changeFormUsername = (event) => {
    setUsername(event.target.value);
  };
  const changeFormFirstName = (event) => {
    setFirstName(event.target.value);
  };
  const changeFormLastName = (event) => {
    setLastName(event.target.value);
  };
  const changeFormAddress = (event) => {
    setAddress(event.target.value);
  };
  const changeFormPhoneNumber = (event) => {
    setPhoneNumber(event.target.value);
  };
  const changeFormPassword = (event) => {
    setPassword(event.target.value);
  };
  const changeFormPasswordRepeat = (event) => {
    setPasswordRepeat(event.target.value);
  };

  const handleRegisterSubmit = async (event) => {
    event.preventDefault();

    const updateProfileRequest = JSON.stringify({
      id: userInformation.id,
      username: formUsername,
      firstName: formFirstName,
      lastName: formLastName,
      address: formAddress,
      phoneNumber: formPhoneNumber,
      password: formPassword,
      passwordRepeat: formPasswordRepeat,
    });

    RegistrationService.registration(updateProfileRequest).then(async (res) => {
      console.log(res);
      if (res.status === 500) {
        alert("All fields could not be blank!");
      } else if (res.status === 400) {
        alert("username or phonenumber already existed!");
      } else if (res.status === 406) {
        alert("password and confirm password are not match!");
      } else if (res.status === 200) {
        alert("User successfully registered!");

        const authenticationRequest = {
          username: formUsername,
          password: formPassword,
        };
        AuthService.login(authenticationRequest).then(async (res) => {
          let loginResponse = {
            token: res.data.token,
            refreshToken: res.data.refreshToken,
            userRoles: res.data.userRoles,
            username: res.data.username,
          };
          dispatch(loginAction(loginResponse));
        });
      }
    });
  };

  return !loginInfo.isLoading ? (
    loginInfo.isLogged ? (
      <Container>
        <Wrapper>
          <Title>UPDATE PROFILE</Title>
          <Form onSubmit={handleRegisterSubmit}>
            <Input
              type="text"
              value={formFirstName}
              placeholder="first name"
              onChange={changeFormFirstName}
            />
            <Input
              type="text"
              value={formLastName}
              placeholder="last name"
              onChange={changeFormLastName}
              minLength="1"
            />
            <Input
              type="text"
              value={formAddress}
              placeholder="address"
              onChange={changeFormAddress}
              minLength="1"
            />
            <Input
              type="text"
              value={formPhoneNumber}
              placeholder="phone number"
              onChange={changeFormPhoneNumber}
              minLength="10"
              maxLength="11"
            />
            <Agreement>
              By creating an account, I consent to the processing of my personal
              data in accordance with the <b>PRIVACY POLICY</b>
            </Agreement>
            <Button>CREATE</Button>
          </Form>
        </Wrapper>
      </Container>
    ) : (
      <Navigate to="/" />
    )
  ) : (
    ""
  );
};

export default UpdateProfile;
