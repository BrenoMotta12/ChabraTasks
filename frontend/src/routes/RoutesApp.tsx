import React from 'react'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import Login from '../pages/Login'
import Home from '../pages/Home'
import useAuth from '../hooks/useAuth'

interface PrivateProps {
    Item: React.ElementType;
  }
  
  const Private = ({Item}: PrivateProps) => {
    const {user} = useAuth();
    
    return user != null ? <Item/> : <Login/>
  }

export const RoutesApp = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path='*'element={<Private Item={Home}/>}/>
        <Route path='/' element={<Login/>}/>
      </Routes>
    </BrowserRouter>
  )
}
