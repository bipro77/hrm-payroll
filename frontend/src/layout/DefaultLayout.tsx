import { useState } from "react";
import { Outlet } from "react-router-dom";

import AppSidebar from "./AppSidebar";
import AppHeader from "./AppHeader";
import AppFooter from "./AppFooter";
import "./layout.css";

const DefaultLayout = () => {
  const [sidebarVisible, setSidebarVisible] = useState(true);

  return (
    <div className="app-layout">
      <AppSidebar visible={sidebarVisible} onVisibleChange={setSidebarVisible} />

      <div className="app-wrapper d-flex flex-column min-vh-100">
        <AppHeader onSidebarToggle={() => setSidebarVisible((visible) => !visible)} />

        <main className="app-body flex-grow-1 px-3 px-lg-4">
          <Outlet />
        </main>

        <AppFooter />
      </div>
    </div>
  );
};

export default DefaultLayout;
