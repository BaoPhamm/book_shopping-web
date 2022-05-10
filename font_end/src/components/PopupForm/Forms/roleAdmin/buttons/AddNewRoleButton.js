import React from "react";
import Button from "@mui/material/Button";

const AddNewRoleButton = ({ buttonRef, showModal }) => {
  return (
    <Button variant="contained" ref={buttonRef} onClick={showModal}>
      +ADD ROLE
    </Button>
  );
};
export default AddNewRoleButton;
