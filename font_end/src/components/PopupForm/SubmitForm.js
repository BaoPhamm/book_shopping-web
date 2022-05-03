import React from "react";
import styled from "styled-components";
import { Link } from "react-router-dom";

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

const Button = styled.button`
  width: 40%;
  border: none;
  padding: 15px 20px;
  background-color: teal;
  color: white;
  cursor: pointer;
  margin-bottom: 1rem;
  margin-top: 1rem;
`;

const TextLink = styled.a`
  margin: 0.5rem;
  font-size: 12px;
  text-decoration: underline;
  cursor: pointer;
`;
const TextLinkNoneUnderLine = styled.p`
  margin: 0.5rem;
  font-size: 12px;
  text-decoration: none;
  cursor: pointer;
`;

const SubmitForm = ({ onSubmit }) => {
  return (
    <Wrapper>
      <form onSubmit={onSubmit}>
        <Input type="text" placeholder="username" />
        <Input type="password" placeholder="password" />
        <Button>LOGIN</Button>
        <TextLinkNoneUnderLine>DON'T HAVE AN ACCOUNT?</TextLinkNoneUnderLine>
        <TextLink>
          <Link to="/register" style={{ color: "black" }}>
            CREATE A NEW ACCOUNT
          </Link>
        </TextLink>
      </form>
    </Wrapper>
  );
};
export default SubmitForm;
