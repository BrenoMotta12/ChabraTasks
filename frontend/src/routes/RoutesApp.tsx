import React from "react";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Login from "../pages/Login";
import Home from "../pages/fragment/Home";
import useAuth from "../hooks/useAuth";
import MainLayout from "../pages/MainLayout";
import Notifications from "../pages/fragment/Notifications";
import Graphs from "../pages/fragment/Graphs";
import SpaceFragment from "../pages/fragment/SpaceFragment";
import ListFragment from "../pages/fragment/ListFragment";
import BoardSubfragment from "../pages/fragment/subfragment/BoardSubfragment";
import ListSubfragment from "../pages/fragment/subfragment/ListSubfragment";
import ActivitySubfragment from "../pages/fragment/subfragment/ActivitySubfragment";
import GraphsSubfragment from "../pages/fragment/subfragment/GraphsSubfragment";

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
          <Route path="space/:id" element={<SpaceFragment />} />
          <Route path="list" element={<ListFragment />}>
              <Route path="board/:id" element={<BoardSubfragment />} />
              <Route path="list/:id" element={<ListSubfragment />} />
              <Route path="activity/:id" element={<ActivitySubfragment />} />
              <Route path="graph/:id" element={<GraphsSubfragment />} />
            </Route>
        </Route>
      </Routes>
    </BrowserRouter>
  );
};
