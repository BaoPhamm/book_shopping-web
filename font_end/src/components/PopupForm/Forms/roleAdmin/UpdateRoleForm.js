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
  grid-template-columns: 10% 90%;
  align-items: center;
  margin-top: 5px;
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
  width: 30%;
  border: none;
  padding: 15px 20px;
  background-color: teal;
  color: white;
  cursor: pointer;
  margin-bottom: 1rem;
  margin-top: 1rem;
`;

const UpdateRoleForm = ({ onSubmit, roleDetails }) => {
  return (
    <Wrapper>
      <Title>UPDATE ROLE</Title>
      <form onSubmit={onSubmit}>
        <RowWrapper>
          <Text>ID:</Text>
          <Input
            disabled={true}
            type="text"
            defaultValue={roleDetails.id}
            name="roleid"
            placeholder="Role ID"
          />
        </RowWrapper>
        <RowWrapper>
          <Text>Name:</Text>
          <Input
            type="text"
            defaultValue={roleDetails.name}
            name="rolename"
            placeholder="Role name"
          />
        </RowWrapper>
        <Button>UPDATE</Button>
      </form>
    </Wrapper>
  );
};
export default UpdateRoleForm;
