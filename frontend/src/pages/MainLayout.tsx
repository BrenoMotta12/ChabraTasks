
import TopBar from './TopBar'
import SideBar from './SideBar'
import { Outlet } from 'react-router-dom'

export default function MainLayout() {
  return (
    <div className='flex flex-col h-screen'>
        <TopBar/>

        <main className='flex h-full w-full pb-4'>
            <SideBar/>
            <Outlet />
        </main>

    </div>
  )
}
