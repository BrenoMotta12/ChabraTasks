
import TopBar from './TopBar'
import SideBar from './SideBar'
import { Outlet } from 'react-router-dom'

export default function MainLayout() {
  return (
    <div className='flex flex-col h-screen'>
        <TopBar/>

        <main className='flex flex-1 overflow-y-auto max-w-screen pb-2 pe-2'>
            <SideBar/>
            <Outlet />
        </main>

    </div>
  )
}
