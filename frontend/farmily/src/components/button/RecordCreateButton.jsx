import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

export default function RecordCreateButton(props) {
  const createURL = `/family/record/create/${props.name}`; // 상대 경로로 변경
  const navigate = useNavigate();

  const onClickHandler = () => {
    navigate(createURL);
  };

  return (
    <div
      onClick={onClickHandler}
      className="flex justify-around items-center h-1/4 m-5 rounded-md border-black hover:bg-gray-200 hover:cursor-pointer"
    >
      <div className="w-1/2">
        <img src={props.img} alt="" className="m-auto" />
      </div>
      <div className="text-left w-1/2">
        <p className="text-2xl">{props.title}</p>
        <p>{props.content}</p>
      </div>
    </div>
  );
}
