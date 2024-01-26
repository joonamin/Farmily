import { useState } from 'react';

import ArticleItem from './ArticleItem.jsx';
import SmallButton from '../components/SmallButton.jsx';

// 테스트용 데이터 TESTITEMS , 데이터 받아오면 삭제

const TESTITEMS = [
  {
    id: '3',
    type: 'daily',
    title:
      'sodales. Mauris blandit enim consequat purus. Maecenas libero est, congue',
    content: 'luctus sit amet, faucibus ut, nulla. Cras eu tellus',
    date: '2024-02-14 19:46:08',
    author: { nickname: 'Abra Baker' },
  },
  {
    id: '1',
    type: 'challenge',
    title: 'sem ut dolor dapibus gravida. Aliquam tincidunt, nunc ac mattis',
    content: 'diam vel arcu. Curabitur',
    date: '2023-09-29 19:48:52',
    author: { nickname: 'Sopoline Hunter' },
  },
  {
    id: '6',
    type: 'challenge',
    title:
      'viverra. Maecenas iaculis aliquet diam. Sed diam lorem, auctor quis,',
    content:
      'nibh. Phasellus nulla. Integer vulputate, risus a ultricies adipiscing,',
    date: '2023-02-02 19:06:52',
    author: { nickname: 'Nicholas Wright' },
  },
  {
    id: '9',
    type: 'challenge',
    title:
      'orci luctus et ultrices posuere cubilia Curae Donec tincidunt. Donec',
    content: 'augue ut lacus. Nulla',
    date: '2024-02-25 18:04:14',
    author: { nickname: 'Ashton Griffin' },
  },
  {
    id: '6',
    type: 'challenge',
    title:
      'nibh. Phasellus nulla. Integer vulputate, risus a ultricies adipiscing, enim',
    content: 'ornare. Fusce',
    date: '2023-01-29 07:13:51',
    author: { nickname: 'Channing Byers' },
  },
  {
    id: '7',
    type: 'event',
    title:
      'Curabitur sed tortor. Integer aliquam adipiscing lacus. Ut nec urna',
    content: 'egestas. Sed pharetra, felis eget varius ultrices, mauris ipsum',
    date: '2023-12-09 15:05:52',
    author: { nickname: 'Barclay Cortez' },
  },
  {
    id: '10',
    type: 'daily',
    title: 'arcu eu odio tristique pharetra. Quisque ac libero nec ligula',
    content: 'Donec vitae erat vel pede blandit',
    date: '2023-04-07 18:48:17',
    author: { nickname: 'Kathleen Espinoza' },
  },
  {
    id: '10',
    type: 'event',
    title: 'odio, auctor vitae, aliquet nec, imperdiet nec, leo. Morbi neque',
    content: 'nonummy. Fusce fermentum fermentum arcu. Vestibulum ante ipsum',
    date: '2023-09-15 01:52:37',
    author: { nickname: 'Brian Adams' },
  },
  {
    id: '7',
    type: 'daily',
    title: 'enim mi tempor lorem, eget mollis lectus pede et risus.',
    content: 'semper cursus.',
    date: '2024-09-19 21:55:09',
    author: { nickname: 'Halee Carr' },
  },
  {
    id: '7',
    type: 'challenge',
    title:
      'rutrum magna. Cras convallis convallis dolor. Quisque tincidunt pede ac',
    content: 'lectus rutrum urna, nec luctus felis purus ac tellus.',
    date: '2024-12-27 15:54:25',
    author: { nickname: 'Joseph Kelly' },
  },
];

export default function ArticleList() {
  const itemsPerPage = 7;
  const [currentPage, setCurrentPage] = useState(1);

  const indexOfLastItem = currentPage * itemsPerPage;
  const indexOfFirstItem = indexOfLastItem - itemsPerPage;
  const currentItems = TESTITEMS.slice(indexOfFirstItem, indexOfLastItem);

  const paginate = (pageNumber) => setCurrentPage(pageNumber);

  return (
    <div className="p-5 h-3/5 overflow-hidden relative">
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
              date={item.date}
              id={item.id}
              type={item.type}
            />
          ))}
        </tbody>
      </table>

      {/* 페이징 컴포넌트 */}
      <div className="flex justify-center mt-6 absolute left-1/2 transform -translate-x-1/2">
        {[...Array(Math.ceil(TESTITEMS.length / itemsPerPage)).keys()].map(
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
      <div className="flex justify-end absolute right-0">
        <SmallButton text="글쓰기" url="create" />
      </div>
    </div>
  );
}
