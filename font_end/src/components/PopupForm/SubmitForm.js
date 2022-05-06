import React from "react";
import styled from "styled-components";

const Wrapper = styled.div`
  width: 90%;
  padding: 20px;
  background-color: white;
  text-align: center;
`;

const Input = styled.input`
  flex: 1;
  min-width: 90%;
  margin: 10px 0;
  padding: 10px;
`;
const Title = styled.h1`
  font-size: 24px;
  font-weight: 300;
`;
const Button = styled.button`
  width: 60%;
  border: none;
  padding: 15px 20px;
  background-color: teal;
  color: white;
  cursor: pointer;
  margin-bottom: 1rem;
  margin-top: 1rem;
`;

const SubmitForm = ({ onSubmit }) => {
  return (
    <Wrapper>
      <Title>CHANGE PASSWORD</Title>
      <form onSubmit={onSubmit}>
        <Input
          type="password"
          name="currentPassword"
          placeholder="current password"
        />
        <Input type="password" name="newPassword" placeholder="new password" />
        <Input
          type="password"
          name="repeatNewPassword"
          placeholder="repeat new password"
        />
        <Button>CHANGE</Button>
      </form>
    </Wrapper>
  );
};
export default SubmitForm;
