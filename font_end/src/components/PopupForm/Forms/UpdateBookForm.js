import React from "react";
import styled from "styled-components";

const Wrapper = styled.div`
  width: 95%;
  background-color: white;
  text-align: center;
`;

const RowWrapper = styled.div`
  justify-content: start;
  width: 100%;
  display: grid;
  grid-template-columns: 30% 70%;
  align-items: center;
  margin-top: 10px;
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

const ChangePasswordForm = ({ onSubmit }) => {
  return (
    <Wrapper>
      <Title>UPDATE BOOK</Title>
      <form onSubmit={onSubmit}>
        <RowWrapper>
          <Text>Current Password:</Text>
          <Input
            type="password"
            name="currentPassword"
            placeholder="current password"
          />
        </RowWrapper>
        <RowWrapper>
          <Text>New Password:</Text>
          <Input
            type="password"
            name="newPassword"
            placeholder="new password"
          />
        </RowWrapper>
        <RowWrapper>
          <Text>Repeat New Password:</Text>
          <Input
            type="password"
            name="repeatNewPassword"
            placeholder="repeat new password"
          />
        </RowWrapper>
        <Button>CHANGE</Button>
      </form>
    </Wrapper>
  );
};
export default ChangePasswordForm;
