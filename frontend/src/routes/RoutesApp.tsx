import React from "react";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Login from "../pages/Login";
import Home from "../pages/fragment/Home";
import useAuth from "../hooks/useAuth";
import MainLayout from "../pages/MainLayout";
import Notifications from "../pages/fragment/Notifications";
import Graphs from "../pages/fragment/Graphs";

interface PrivateProps {
  children: React.ReactNode;
}

const Private = ({ children }: PrivateProps) => {
  const { user } = useAuth();
  console.log(user);
  return user ? children : <Login />;
};

export const RoutesApp = () => {
  return (
    <BrowserRouter>
      <Routes>
        {/* Rota pública para Login */}
        <Route path="/" element={<Login />} />

        
        <Route path="/" element={
            <Private>
              <MainLayout />
            </Private>
          }
        >
          <Route path="home" element={<Home />} />
          <Route path="notifications" element={<Notifications />} />
          <Route path="graphs" element={<Graphs />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
};
