import TopBar from './TopBar'
import SideBar from './SideBar'
import { Outlet } from 'react-router-dom'
import { useState } from 'react';
import ModalUsers from './ModalUsers';

export default function MainLayout() {

  const [isModalOpen, setIsModalOpen] = useState(false);

  return (
    <div className='flex flex-col h-screen'>
      <TopBar onOpenModal={() => setIsModalOpen(true)} />

      <main className='flex flex-1 overflow-y-auto max-w-screen pb-2 pe-2'>
        <SideBar />
        <Outlet />
      </main>

      {isModalOpen && (
        <ModalUsers isModalOpen={isModalOpen} setIsModalOpen={setIsModalOpen} />
      )}

    </div>
  );
}
