import React, { Component } from "react";
import { Modal } from "../Modal";
import ChangPasswordButton from "../Forms/bookAdmin/buttons/ChangPasswordButton";
import AddBookButton from "../Forms/bookAdmin/buttons/AddBookButton";
import UpdateBookButton from "../Forms/bookAdmin/buttons/UpdateBookButton";
import AddCatToBookButton from "../Forms/bookAdmin/buttons/AddCatToBookButton";
import RemoveCatFromBookButton from "../Forms/bookAdmin/buttons/RemoveCatFromBookButton";
import DeleteBookButton from "../Forms/bookAdmin/buttons/DeleteBookButton";
import AddRoleToUserButton from "../Forms/userAdmin/buttons/AddRoleToUserButton";
import RemoveRoleFromUserButton from "../Forms/userAdmin/buttons/RemoveRoleFromUserButton";
import DeleteUserButton from "../Forms/userAdmin/buttons/DeleteUserButton";
import BlockUserSwitch from "../Forms/userAdmin/buttons/BlockUserSwitch";
import AddNewRoleButton from "../Forms/roleAdmin/buttons/AddNewRoleButton";
import UpdateRoleButton from "../Forms/roleAdmin/buttons/UpdateRoleButton";
import DeleteRoleButton from "../Forms/roleAdmin/buttons/DeleteRoleButton";
import AddNewCategoryButton from "../Forms/categoryAdmin/buttons/AddNewCategoryButton";
import UpdateCategoryButton from "../Forms/categoryAdmin/buttons/UpdateCategoryButton";
import DeleteCategoryButton from "../Forms/categoryAdmin/buttons/DeleteCategoryButton";
import "./index.css";

export class PopupContainer extends Component {
  state = { isShown: false, newBookRatingValue: 0 };
  showModal = () => {
    this.setState({ isShown: true }, () => {
      this.closeButton.focus();
    });
    this.toggleScrollLock();
  };
  closeModal = () => {
    this.setState({ isShown: false });
    this.TriggerButton.focus();
    this.toggleScrollLock();
  };
  onKeyDown = (event) => {
    if (event.keyCode === 27) {
      this.closeModal();
    }
  };

  toggleScrollLock = () => {
    document.querySelector("html").classList.toggle("scroll-lock");
  };

  GetButton = () => {
    if (this.props.typeSubmit === "changePassword") {
      return (
        <ChangPasswordButton
          showModal={this.showModal}
          buttonRef={(n) => (this.TriggerButton = n)}
        />
      );
    }
    // BOOK
    else if (this.props.typeSubmitGroup === "book") {
      if (this.props.typeSubmit === "addNew") {
        return (
          <AddBookButton
            showModal={this.showModal}
            buttonRef={(n) => (this.TriggerButton = n)}
          />
        );
      } else if (this.props.typeSubmit === "update") {
        return (
          <UpdateBookButton
            showModal={this.showModal}
            buttonRef={(n) => (this.TriggerButton = n)}
          />
        );
      } else if (this.props.typeSubmit === "addCat") {
        return (
          <AddCatToBookButton
            showModal={this.showModal}
            buttonRef={(n) => (this.TriggerButton = n)}
          />
        );
      } else if (this.props.typeSubmit === "removeCat") {
        return (
          <RemoveCatFromBookButton
            showModal={this.showModal}
            buttonRef={(n) => (this.TriggerButton = n)}
          />
        );
      } else if (this.props.typeSubmit === "delete") {
        return (
          <DeleteBookButton
            showModal={this.showModal}
            buttonRef={(n) => (this.TriggerButton = n)}
          />
        );
      }
    }
    // USER
    else if (this.props.typeSubmitGroup === "user") {
      if (this.props.typeSubmit === "addRole") {
        return (
          <AddRoleToUserButton
            showModal={this.showModal}
            buttonRef={(n) => (this.TriggerButton = n)}
            isAdminManager={this.props.isAdminManager}
          />
        );
      } else if (this.props.typeSubmit === "removeRole") {
        return (
          <RemoveRoleFromUserButton
            showModal={this.showModal}
            buttonRef={(n) => (this.TriggerButton = n)}
            isAdminManager={this.props.isAdminManager}
          />
        );
      } else if (this.props.typeSubmit === "delete") {
        return (
          <DeleteUserButton
            showModal={this.showModal}
            buttonRef={(n) => (this.TriggerButton = n)}
          />
        );
      } else if (this.props.typeSubmit === "block") {
        return (
          <BlockUserSwitch
            showModal={this.showModal}
            buttonRef={(n) => (this.TriggerButton = n)}
            userDetails={this.props.userDetails}
          />
        );
      }
    }
    // ROLE
    else if (this.props.typeSubmitGroup === "role") {
      if (this.props.typeSubmit === "addNew") {
        return (
          <AddNewRoleButton
            showModal={this.showModal}
            buttonRef={(n) => (this.TriggerButton = n)}
          />
        );
      } else if (this.props.typeSubmit === "update") {
        return (
          <UpdateRoleButton
            showModal={this.showModal}
            buttonRef={(n) => (this.TriggerButton = n)}
            roleDetails={this.props.roleDetails}
          />
        );
      } else if (this.props.typeSubmit === "delete") {
        return (
          <DeleteRoleButton
            showModal={this.showModal}
            buttonRef={(n) => (this.TriggerButton = n)}
            roleDetails={this.props.roleDetails}
          />
        );
      }
    }
    // CATEGORY
    else if (this.props.typeSubmitGroup === "category") {
      if (this.props.typeSubmit === "addNew") {
        return (
          <AddNewCategoryButton
            showModal={this.showModal}
            buttonRef={(n) => (this.TriggerButton = n)}
          />
        );
      } else if (this.props.typeSubmit === "update") {
        return (
          <UpdateCategoryButton
            showModal={this.showModal}
            buttonRef={(n) => (this.TriggerButton = n)}
          />
        );
      } else if (this.props.typeSubmit === "delete") {
        return (
          <DeleteCategoryButton
            showModal={this.showModal}
            buttonRef={(n) => (this.TriggerButton = n)}
          />
        );
      }
    }
  };

  render() {
    return (
      <React.Fragment>
        {this.GetButton()}
        {this.state.isShown ? (
          <Modal
            onSubmit={this.props.onSubmit}
            modalRef={(n) => (this.modal = n)}
            buttonRef={(n) => (this.closeButton = n)}
            closeModal={this.closeModal}
            onKeyDown={this.onKeyDown}
            typeSubmitGroup={this.props.typeSubmitGroup}
            typeSubmit={this.props.typeSubmit}
            productDetails={this.props.productDetails}
            allCategories={this.props.allCategories}
            userDetails={this.props.userDetails}
            roleDetails={this.props.roleDetails}
            categoryDetails={this.props.categoryDetails}
            allRoles={this.props.allRoles}
          />
        ) : null}
      </React.Fragment>
    );
  }
}

export default PopupContainer;
