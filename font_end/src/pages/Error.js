import styled from "styled-components";
import React from "react";

const Errormsg = styled.h1`
  font-size: 70px;
  text-align: center;
`;

const Error = () => <Errormsg>404 NOT FOUND</Errormsg>;

export default Error;
