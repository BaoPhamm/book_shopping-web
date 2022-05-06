import React, { useState, useEffect } from "react";
import styled from "styled-components";
import { mobile } from "../responsive";
import { Navigate } from "react-router-dom";
import { useSelector } from "react-redux";
import { loginSelector } from "../store/reducers/loginSlice";
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
  margin: 20px 0px 0px 0px;
  padding: 10px;
`;

const Button = styled.button`
  width: 50%;
  border: none;
  left: 50%;
  padding: 15px 20px;
  background-color: teal;
  color: white;
  cursor: pointer;
  margin-top: 20px;
`;

const UpdateProfile = () => {
  const [formFirstName, setFirstName] = useState("");
  const [formLastName, setLastName] = useState("");
  const [formAddress, setAddress] = useState("");
  const [formPhoneNumber, setPhoneNumber] = useState("");
  const [isLoading, setIsLoading] = useState(true);

  const loginInfo = useSelector(loginSelector);

  useEffect(() => {
    fetchData();
  }, [loginInfo.isLogged]);

  const fetchData = async () => {
    await setIsLoading(true);
    if (loginInfo.isLogged) {
      await setFirstName(loginInfo.firstName);
      console.log(loginInfo.firstName);
      await setLastName(loginInfo.lastName);
      await setAddress(loginInfo.address);
      await setPhoneNumber(loginInfo.phoneNumber);
    }
    await setIsLoading(false);
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

  const handleUpdateSubmit = async (event) => {
    event.preventDefault();

    const updateProfileRequest = JSON.stringify({
      firstName: formFirstName,
      lastName: formLastName,
      address: formAddress,
      phoneNumber: formPhoneNumber,
    });

    UserService.updateUserInfo(updateProfileRequest).then(async (res) => {
      console.log(res);
      if (res.status === 400) {
        if (res.data.errorMessage === "First name cannot be empty") {
          alert("First name cannot be empty");
        } else if (res.data.errorMessage === "Last name cannot be empty") {
          alert("Last name cannot be empty");
        } else if (
          res.data.errorMessage ===
          "The phone number must be has atleast 4 characters long"
        ) {
          alert("The phone number must be has atleast 4 characters long");
        } else if (res.data.errorMessage === "The address cannot be empty") {
          alert("The address cannot be empty");
        }
      } else if (res.status === 200) {
        alert("User profile successfully updated!");
      }
    });
  };

  return !loginInfo.isLoading ? (
    !isLoading ? (
      loginInfo.isLogged ? (
        <Container>
          <Wrapper>
            <Title>UPDATE PROFILE</Title>
            <Form onSubmit={handleUpdateSubmit}>
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
              <Button>UPDATE</Button>
            </Form>
            <Button>CHANGE PASSWORD</Button>
          </Wrapper>
        </Container>
      ) : (
        <Navigate to="/login" />
      )
    ) : (
      ""
    )
  ) : (
    ""
  );
};

export default UpdateProfile;
