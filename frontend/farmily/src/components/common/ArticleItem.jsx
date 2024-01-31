import { useNavigate } from 'react-router-dom';

import DailyFruit from '../../assets/images/dailyFruit.png';
import EventFruit from '../../assets/images/EventFruit.png';
import ChallengeFruit from '../../assets/images/ChallengeFruit.png';
import { useState } from 'react';

export default function ArticleItem(article) {
  const [fruitImg, setFruitImg] = useState();

  const URL = `/family/record/${article.type.toLowerCase()}/${article.id}`;
  const navigate = useNavigate();

  const onClickHandler = () => {
    navigate(URL);
  };

  // article.type에 따라서 적절한 이미지 설정
  if (article.type === 'DAILY') {
    setFruitImg('🍌');
  } else if (article.type === 'EVENT') {
    setFruitImg('🍎');
  } else if (article.type === 'CHALLENGE') {
    setFruitImg('🍇');
  }

  const formattedDate = new Date(article.createdAt).toLocaleDateString();
  return (
    // 게시글 디테일 페이지 만든 후 Link 달아주기
    <>
      <tr
        className="border-b-2 cursor-pointer hover:bg-gray-300"
        onClick={onClickHandler}
      >
        <td>{fruitImg}</td>
        <td className="truncate">{article.title}</td>
        <td className="truncate">{article.nickname}</td>
        <td>{formattedDate}</td> {/* 년월일만 표시 */}
      </tr>
    </>
  );
}
