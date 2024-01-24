import ArticleItem from './ArticleItem.jsx'

// 테스트용 데이터 TESTITEMS , 데이터 받아오면 삭제

const TESTITEMS = [
  {'title': '제목', 'author': {nickname: '작성자'}, 'date': '2024-01-20'},
  {'title': '제목1', 'author': {nickname: '작성자'}, 'date': '2024-01-21'},
  {'title': '제목2', 'author': {nickname: '작성자'}, 'date': '2024-01-22'},
  {'title': '제목3', 'author': {nickname: '작성자'}, 'date': '2024-01-23'},
  {'title': '제목4', 'author':  {nickname: '작성자'}, 'date': '2024-01-24'},
  {'title': '제목5', 'author':  {nickname: '작성자'}, 'date': '2024-01-25'},
  {'title': '제목5', 'author':  {nickname: '작성자'}, 'date': '2024-01-25'},
  {'title': '제목5', 'author':  {nickname: '작성자'}, 'date': '2024-01-25'},
  {'title': '제목5', 'author':  {nickname: '작성자'}, 'date': '2024-01-25'},
  {'title': '제목5', 'author':  {nickname: '작성자'}, 'date': '2024-01-25'},
  {'title': '제목5', 'author':  {nickname: '작성자'}, 'date': '2024-01-25'},
  {'title': '제목5', 'author':  {nickname: '작성자'}, 'date': '2024-01-25'},
  {'title': '제목5', 'author':  {nickname: '작성자'}, 'date': '2024-01-25'},
  {'title': '제목5', 'author':  {nickname: '작성자'}, 'date': '2024-01-25'},
  {'title': '제목5', 'author':  {nickname: '작성자'}, 'date': '2024-01-25'},
]

export default function ArticleList() {
  return (
    <div className="p-5 h-1/2 overflow-hidden">
      <table className="table-fixed w-full">
        <thead className="border-b-4 border-gray-500">
          <tr>
            <th>제목</th>
            <th>작성자</th>
            <th>작성일자</th>
          </tr>
        </thead>

        {/* 데이터 받아오면 TESTITEMS 수정 */}
        {/* 페이징 필요 */}
        <tbody>
          {TESTITEMS.map((item, index) => (
            <ArticleItem key={index} title={item.title} nickname={item.author.nickname} content={item.content} date={item.date} />
            ))}
        </tbody>
      </table>
    </div>
  )
}