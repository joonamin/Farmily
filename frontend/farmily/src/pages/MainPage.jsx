import { useState, useEffect } from 'react';

import MainTree from '../components/tree/MainTree.jsx';
import Challenge from '../components/tree/Challenge.jsx';
import Board from '../components/tree/Board.jsx';

import { useDispatch } from 'react-redux';
import { getAccessToken } from '../store/auth.jsx';

export default function MainPage() {
  const dispatch = useDispatch();

  const tokenString = document.cookie.match('accessToken').input;

  const accessToken = tokenString.split('accessToken=')[1];
  // console.log(accessToken);

  useEffect(() => {
    dispatch(getAccessToken({ accessToken: accessToken }));
  }, []);

  return (
    <>
      <Challenge />
      <MainTree />
      <Board title="나무 꾸미기" />
    </>
  );
}
