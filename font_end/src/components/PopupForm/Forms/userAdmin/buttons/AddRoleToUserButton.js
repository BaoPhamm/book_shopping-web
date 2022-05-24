import React from "react";
import Button from "@mui/material/Button";

const AddRoleToUserButton = ({ buttonRef, showModal, isAdminManager }) => {
  return (
    <Button
      variant="contained"
      color="success"
      disabled={!isAdminManager}
      ref={buttonRef}
      onClick={showModal}
    >
      +ROLE
    </Button>
  );
};
export default AddRoleToUserButton;
