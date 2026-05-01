import { NavLink } from "react-router-dom";
import {
  CNavGroup,
  CNavItem,
  CNavLink,
  CNavTitle,
  CSidebar,
  CSidebarBrand,
  CSidebarHeader,
  CSidebarNav,
} from "@coreui/react";

interface AppSidebarProps {
  visible: boolean;
  onVisibleChange: (visible: boolean) => void;
}

const AppSidebar = ({ visible, onVisibleChange }: AppSidebarProps) => {
  return (
    <CSidebar
      className="app-sidebar border-end"
      colorScheme="dark"
      position="fixed"
      visible={visible}
      onVisibleChange={onVisibleChange}
    >
      <CSidebarHeader className="border-bottom">
        <CSidebarBrand as={NavLink} to="/employees" className="text-decoration-none">
          HRM Payroll
        </CSidebarBrand>
      </CSidebarHeader>

      <CSidebarNav>
        <CNavTitle>Management</CNavTitle>
        <CNavItem>
          <CNavLink as={NavLink} to="/employees">
            Employees
          </CNavLink>
        </CNavItem>

        <CNavGroup toggler="Payroll">
          <CNavItem>
            <CNavLink disabled href="#">
              Salary
            </CNavLink>
          </CNavItem>
          <CNavItem>
            <CNavLink disabled href="#">
              Reports
            </CNavLink>
          </CNavItem>
        </CNavGroup>
      </CSidebarNav>
    </CSidebar>
  );
};

export default AppSidebar;
