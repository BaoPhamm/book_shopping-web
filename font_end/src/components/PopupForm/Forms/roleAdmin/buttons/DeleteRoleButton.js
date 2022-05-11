import React from "react";
import Button from "@mui/material/Button";

const DeleteRoleButton = ({ buttonRef, showModal, roleDetails }) => {
  return (
    <Button
      disabled={roleDetails.name === "ADMIN"}
      variant="contained"
      color="error"
      ref={buttonRef}
      onClick={showModal}
    >
      DELETE
    </Button>
  );
};
export default DeleteRoleButton;
