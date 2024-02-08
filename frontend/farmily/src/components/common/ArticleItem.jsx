import { useNavigate } from 'react-router-dom';
import { useState } from 'react';
import { useSelector } from 'react-redux';
import fruitImages from '../../api/fruitImages.jsx';

export default function ArticleItem(article) {
  const [fruitImg, setFruitImg] = useState();
  const family = useSelector((state) => state.family.value);
  const URL = `/family/record/${article.type.toLowerCase()}/${article.id}`;
  const navigate = useNavigate();

  const onClickHandler = () => {
    navigate(URL);
  };
  // console.log(family);
  // article.type에 따라서 적절한 이미지 설정
  if (article.type === 'DAILY') {
    setFruitImg(fruitImages[family.fruitSkins.daily]);
  } else if (article.type === 'EVENT') {
    setFruitImg(fruitImages[family.fruitSkins.event]);
  } else if (article.type === 'CHALLENGE') {
    setFruitImg(fruitImages[family.fruitSkins.challenge]);
  }

  const formattedDate = new Date(article.createdAt).toLocaleDateString();
  return (
    // 게시글 디테일 페이지 만든 후 Link 달아주기
    <>
      <tr
        className={`border-b-2 cursor-pointer hover:bg-gray-300`}
        onClick={onClickHandler}
      >
        <td className="flex justify-center">
          <img src={fruitImg} alt="fruit" className="h-6" />
        </td>
        <td className="truncate">{article.title}</td>
        <td className="truncate">{article.nickname}</td>
        <td>{formattedDate}</td> {/* 년월일만 표시 */}
      </tr>
    </>
  );
}
