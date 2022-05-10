import React, { useState, useEffect } from "react";
import styled from "styled-components";
import { mobile } from "../responsive";
import { Navigate } from "react-router-dom";
import { useSelector } from "react-redux";
import { loginSelector } from "../store/reducers/loginSlice";
import UserService from "../services/user/UserService";
import { PopupContainer } from "../components/PopupForm/Container/index";

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

const RowWrapper = styled.div`
  justify-content: start;
  width: 100%;
  display: grid;
  grid-template-columns: 18% 82%;
  align-items: center;
  margin-top: 20px;
`;

const Text = styled.p`
  font-size: 15px;
  text-align: left;
`;

const Input = styled.input`
  min-width: 80%;
  padding: 7px;
  right: 0;
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
  const [dataChange, toggleDataChange] = useState(false);
  const loginInfo = useSelector(loginSelector);

  useEffect(() => {
    fetchData();
  }, [loginInfo.isLogged, dataChange]);

  const fetchData = async () => {
    await setIsLoading(true);
    if (loginInfo.isLogged) {
      await setFirstName(loginInfo.firstName);
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
        if (res.data.message === "First name cannot be empty") {
          alert("First name cannot be empty");
        } else if (res.data.message === "Last name cannot be empty") {
          alert("Last name cannot be empty");
        } else if (
          res.data.message ===
          "The phone number must be has atleast 4 characters long"
        ) {
          alert("The phone number must be has atleast 4 characters long");
        } else if (res.data.message === "The address cannot be empty") {
          alert("The address cannot be empty");
        }
      } else if (res.status === 200) {
        alert("User profile successfully updated!");
      }
    });
  };

  const onChangePasswordSubmit = (event) => {
    event.preventDefault(event);

    const changePasswordRequest = JSON.stringify({
      currentPassword: event.target.currentPassword.value,
      newPassword: event.target.newPassword.value,
      newPasswordRepeat: event.target.repeatNewPassword.value,
    });

    UserService.updateUserPassword(changePasswordRequest).then(async (res) => {
      console.log(res);
      if (res.status === 400) {
        alert(res.data.message);
      } else if (res.status === 406) {
        if (res.data.message === "Passwords do not match.") {
          alert("Passwords do not match.");
        }
      } else if (res.status === 200) {
        alert("Password successfully changed!");
        await toggleDataChange(!dataChange);
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
              <RowWrapper>
                <Text>First name:</Text>
                <Input
                  type="text"
                  value={formFirstName}
                  placeholder="first name"
                  onChange={changeFormFirstName}
                />
              </RowWrapper>
              <RowWrapper>
                <Text>Last name:</Text>
                <Input
                  type="text"
                  value={formLastName}
                  placeholder="last name"
                  onChange={changeFormLastName}
                  minLength="1"
                />
              </RowWrapper>
              <RowWrapper>
                <Text>Address:</Text>
                <Input
                  type="text"
                  value={formAddress}
                  placeholder="address"
                  onChange={changeFormAddress}
                  minLength="1"
                />
              </RowWrapper>
              <RowWrapper>
                <Text>Phone number:</Text>
                <Input
                  type="text"
                  value={formPhoneNumber}
                  placeholder="phone number"
                  onChange={changeFormPhoneNumber}
                  minLength="10"
                  maxLength="11"
                />
              </RowWrapper>
              <Button>UPDATE</Button>
            </Form>
            <PopupContainer
              onSubmit={onChangePasswordSubmit}
              typeSubmit="changePassword"
            />
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
