import { useState, useEffect } from 'react';
import ArticleItem from './ArticleItem.jsx';
import axios from '../../api/axios.jsx';

export default function ArticleList() {
  const [records, setRecords] = useState([
    {
      id: 0,
      type: '',
      title: '',
      content: '',
      createdAt: '',
      author: { nickname: '' },
    },
  ]);

  useEffect(() => {
    // 유저 인증 후 sprintId 적절히 넣기
    axios
      .get('/sprint/1/record')
      .then((response) => {
        setRecords(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  const itemsPerPage = 7;
  const [currentPage, setCurrentPage] = useState(1);

  const indexOfLastItem = currentPage * itemsPerPage;
  const indexOfFirstItem = indexOfLastItem - itemsPerPage;
  const currentItems = records.slice(indexOfFirstItem, indexOfLastItem);

  const paginate = (pageNumber) => setCurrentPage(pageNumber);

  return (
    <div className="px-5 h-2/5 relative">
      <table className="table-fixed w-full">
        <thead className="border-b-4 border-gray-500">
          <tr className="w-full">
            <th className="w-1/12"></th>
            <th className="w-6/12">제목</th>
            <th className="w-2/12">작성자</th>
            <th className="w-3/12">작성일자</th>
          </tr>
        </thead>

        <tbody>
          {currentItems.map((item, index) => (
            <ArticleItem
              key={index}
              title={item.title}
              nickname={item.author.nickname}
              content={item.content}
              createdAt={item.createdAt}
              id={item.id}
              type={item.type}
            />
          ))}
        </tbody>
      </table>

      {/* 페이징 컴포넌트 */}
      <div className="flex justify-center mt-6 absolute left-1/2 transform -translate-x-1/2 -bottom-10">
        {[...Array(Math.ceil(records.length / itemsPerPage)).keys()].map(
          (number) => (
            <button
              key={number + 1}
              onClick={() => paginate(number + 1)}
              className={`px-3 py-1 mx-1 border ${
                currentPage === number + 1 ? 'bg-gray-300' : 'bg-white'
              }`}
            >
              {number + 1}
            </button>
          )
        )}
      </div>
    </div>
  );
}
