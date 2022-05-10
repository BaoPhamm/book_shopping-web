import React from "react";
import styled from "styled-components";

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
const ChangPasswordButton = ({ buttonRef, showModal }) => {
  return (
    <Button ref={buttonRef} onClick={showModal}>
      CHANGE PASSWORD
    </Button>
  );
};
export default ChangPasswordButton;
