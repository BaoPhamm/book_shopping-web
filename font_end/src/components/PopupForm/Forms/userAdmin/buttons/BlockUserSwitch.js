import React from "react";
import FormGroup from "@mui/material/FormGroup";
import FormControlLabel from "@mui/material/FormControlLabel";
import Switch from "@mui/material/Switch";

const BlockUserSwitch = ({ buttonRef, showModal, userDetails }) => {
  return (
    <FormGroup>
      <FormControlLabel
        control={
          <Switch
            onClick={showModal}
            ref={buttonRef}
            checked={userDetails.blocked}
            size="small"
            color="error"
          />
        }
        label={userDetails.blocked ? "Blocked" : "Block"}
      />
    </FormGroup>
  );
};
export default BlockUserSwitch;
