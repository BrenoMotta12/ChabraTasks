import React from 'react'
import homeIcon from '../assets/home.svg'
import bellIcon from '../assets/bell.svg'
import graphIcon from '../assets/graph.svg'
import SideBarOption from '../components/SideBarOption'

export default function SideBar() {
  return (
    <main className='w-100'>
        
        <div className='flex flex-col gap-2 px-4 py-5'>
            
            <SideBarOption
                text='Início'
                icon={homeIcon}
                link='/home'
            />

            <SideBarOption
                text='Notificações'
                icon={bellIcon}
                link='/notifications'
            />

            <SideBarOption
                text='Gráficos'
                icon={graphIcon}
                link='/graphs'
            />
        </div>

        <div className='bg-tertiary w-full h-[1px]'></div>

        
    </main>
  )
}
