import React from 'react'
import Fragment from '../../components/Fragment'
import bellIcon from '../../assets/bell.svg'

export default function Notifications() {
  return (
    <Fragment
    title='Notificações'
    Icon={<img src={bellIcon}/>}
    >
      <div>Notifications</div>
      
    </Fragment>
  )
}
