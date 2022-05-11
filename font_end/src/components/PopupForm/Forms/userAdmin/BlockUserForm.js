import React from "react";
import styled from "styled-components";

const Wrapper = styled.div`
  width: 100%;
  background-color: white;
  display: flex;
  flex-direction: column;
`;

const Text = styled.p`
  font-size: 20px;
  text-align: left;
`;

const Title = styled.h1`
  font-size: 24px;
  font-weight: 300;
  text-align: center;
`;
const ButtonContainer = styled.div`
  width: 100%;
  background-color: white;
  text-align: center;
  margin-top: 1rem;
  margin-bottom: 1.2rem;
`;
const YesButton = styled.button`
  width: 15%;
  border: none;
  padding: 15px 20px;
  background-color: teal;
  color: white;
  cursor: pointer;
  margin-right: 4rem;
`;
const NoButton = styled.button`
  width: 15%;
  border: none;
  padding: 15px 20px;
  background-color: teal;
  color: white;
  cursor: pointer;
  margin-left: 4rem;
`;
const RowWrapper = styled.div`
  justify-content: start;
  width: 100%;
  display: grid;
  grid-template-columns: 18% 82%;
  align-items: center;
  margin-top: 2px;
`;
const Input = styled.input`
  font-size: 14px;
  min-width: 80%;
  padding: 7px;
  right: 0;
`;

const BlockUserForm = ({ onSubmit, onNoSubmit, userDetails }) => {
  return (
    <Wrapper>
      <Title>
        Are you sure to {userDetails.blocked ? "unblock" : "block"} this user?
      </Title>
      <form onSubmit={onSubmit}>
        <RowWrapper>
          <Text>ID:</Text>
          <Input
            disabled={true}
            type="number"
            defaultValue={userDetails.id}
            name="id"
          />
        </RowWrapper>
        <RowWrapper>
          <Text>Username:</Text>
          <Input
            disabled={true}
            type="text"
            defaultValue={userDetails.username}
            name="username"
          />
        </RowWrapper>
        <RowWrapper>
          <Text>Block status:</Text>
          <Input
            disabled={true}
            type="text"
            defaultValue={userDetails.blocked}
            name="blockstatus"
          />
        </RowWrapper>
        <ButtonContainer>
          <YesButton type="submit">YES</YesButton>
          <NoButton onClick={onNoSubmit}>NO</NoButton>
        </ButtonContainer>
      </form>
    </Wrapper>
  );
};
export default BlockUserForm;
