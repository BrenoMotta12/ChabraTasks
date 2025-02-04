import React, { ElementType, ReactNode } from 'react'

interface FragmentProps {
    Icon: ReactNode;
    title: string;
    children: ReactNode;
}

export default function Fragment({children, Icon, title}: FragmentProps) {

    
  return (
    <div className='rounded-xl border-tertiary border-1 w-full h-full me-4'>
        <div className='flex items-center gap-2 p-4 border-b-1 border-tertiary'>
            {Icon}
            <h1 className='text-xl'>{title}</h1>
        </div>
        
        {children}
    </div>
  )
}
