import React from "react";
import Button from "@mui/material/Button";

const DeleteRoleButton = ({ buttonRef, showModal, roleDetails }) => {
  return (
    <Button
      variant="contained"
      color="error"
      ref={buttonRef}
      disabled={roleDetails.name === "ADMIN" || roleDetails.name === "USER"}
      onClick={showModal}
    >
      DELETE
    </Button>
  );
};
export default DeleteRoleButton;
