import { Link } from 'react-router-dom';

export default function LargeButton(btn) {
  return (
    <Link to={btn.url}>
      <div>
        <div className="border-4 border-black rounded-md w-80 h-80 m-6 bg-gray-200">
          <img src={`${btn.image}`} alt="" className="m-auto p-10" />
        </div>
        <p className="text-xl font-semibold">{btn.text}</p>
      </div>
    </Link>
  );
}
