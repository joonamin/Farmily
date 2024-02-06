import { useNavigate } from 'react-router-dom';

import { useState } from 'react';

export default function ChallengeItem(article) {
  const URL = `/family/record/${article.type.toLowerCase()}/${article.id}`;
  const navigate = useNavigate();

  const onClickHandler = () => {
    navigate(URL);
  };

  const formattedDate = new Date(article.createdAt).toLocaleDateString();
  return (
    // 게시글 디테일 페이지 만든 후 Link 달아주기
    <>
      <tr
        className={`border-b-2 cursor-pointer bg-amber-50 hover:bg-amber-200`}
        onClick={onClickHandler}
      >
        <td>Challenge</td>
        <td className="truncate">{article.title}</td>
        <td className="truncate">{article.nickname}</td>
        <td>{formattedDate}</td> {/* 년월일만 표시 */}
      </tr>
    </>
  );
}
