import React, { ReactNode } from 'react'
import { NavLink } from 'react-router-dom';

interface SideBarOptionProps {
    text: string;
    icon: string;
    link: string;
}

export default function SideBarOption({text, icon, link}: SideBarOptionProps) {
  return (
    <NavLink
        to={link}
        className={({ isActive }) => (isActive ? 'bg-bg-selected text-selected rounded-xl' : 'text-text-primary')}
    >
        <div className='flex items-center w-full gap-4 p-2'>
            <img src={icon} alt="" className='h-7 '/>
            <h1 className='text-xl'>{text}</h1>
        </div>
    </NavLink>
  )
}
