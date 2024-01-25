import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

export default function RecordCreateButton(props) {
  const createURL = `/family/record/create/${props.name}`; // 상대 경로로 변경
  const navigate = useNavigate();

  const onClickHandler = () => {
    navigate(createURL);
  };

  return (
    <div onClick={onClickHandler}>
      <img src={props.img} alt="" />
      <p>{props.title}</p>
    </div>
  );
}
