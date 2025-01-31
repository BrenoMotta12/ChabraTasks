import React, { ReactNode } from 'react'

interface FragmentProps {
    icon: string;
    title: string;
    children: ReactNode;
}

export default function Fragment({children, icon, title}: FragmentProps) {

    
  return (
    <div className='rounded-xl border-tertiary border-1 w-full h-full me-4'>
        <div className='flex items-center gap-4 p-4 border-b-1 border-tertiary'>
        <img src={icon} alt="" className='h-7 '/>
            <h1 className='text-xl'>{title}</h1>
        </div>
        {children}
    </div>
  )
}
